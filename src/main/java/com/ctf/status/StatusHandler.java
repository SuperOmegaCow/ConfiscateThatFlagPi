package com.ctf.status;

import io.netty.channel.Channel;

public abstract class StatusHandler {

    public abstract void handle(Channel channel);

}
