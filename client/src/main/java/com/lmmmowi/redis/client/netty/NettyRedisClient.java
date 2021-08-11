package com.lmmmowi.redis.client.netty;

import com.lmmmowi.redis.client.ClientConfiguration;
import com.lmmmowi.redis.client.ClientProcessor;
import com.lmmmowi.redis.client.RedisClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;
import lombok.AccessLevel;
import lombok.Setter;

public class NettyRedisClient implements RedisClient {

    @Setter
    private ClientConfiguration configuration;

    @Setter
    private ClientProcessor clientProcessor;

    @Setter(AccessLevel.PRIVATE)
    private Channel channel;

    private EventLoopGroup eventLoopGroup;

    @Override
    public void startup() {
        this.eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new RedisDecoder());
                        pipeline.addLast(new RedisBulkStringAggregator());
                        pipeline.addLast(new RedisArrayAggregator());
                        pipeline.addLast(new RedisEncoder());
                        pipeline.addLast(new NettyClientChannelHandler(clientProcessor, NettyRedisClient.this::setChannel));
                    }
                });

        ChannelFuture future = bootstrap.connect(configuration.getHost(), configuration.getPort());
        future.syncUninterruptibly();
    }

    @Override
    public void shutdown() {
        eventLoopGroup.shutdownGracefully().syncUninterruptibly();
    }

    @Override
    public void send(String command) {
        if (channel == null) {
            throw new IllegalStateException("connection to redis server is not prepared");
        }

        channel.writeAndFlush(command);
    }
}
