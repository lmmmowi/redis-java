package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.configuration.ServerConfiguration;
import com.lmmmowi.redis.server.RedisServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;
import lombok.Setter;

public class NettyRedisServer implements RedisServer {

    @Setter
    private ServerConfiguration configuration;

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;

    @Override
    public void startup() {
        parentGroup = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new RedisDecoder());
                        pipeline.addLast(new RedisBulkStringAggregator());
                        pipeline.addLast(new RedisArrayAggregator());
                        pipeline.addLast(new RedisEncoder());
                        pipeline.addLast(new NettyServerChannelHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(configuration.getPort());
        channelFuture.syncUninterruptibly();
    }

    @Override
    public void shutdown() {
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
