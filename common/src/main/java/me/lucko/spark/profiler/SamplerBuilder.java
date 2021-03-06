package me.lucko.spark.profiler;

import com.google.common.base.Preconditions;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Builds {@link Sampler} instances.
 */
public class SamplerBuilder {

    private int samplingInterval = 10;
    private long timeout = -1;
    private ThreadDumper threadDumper = ThreadDumper.ALL;
    private ThreadGrouper threadGrouper = ThreadGrouper.BY_NAME;

    public SamplerBuilder() {
    }

    public SamplerBuilder samplingInterval(int samplingInterval) {
        this.samplingInterval = samplingInterval;
        return this;
    }

    public SamplerBuilder completeAfter(long timeout, TimeUnit unit) {
        Preconditions.checkArgument(timeout > 0, "time > 0");
        this.timeout = System.currentTimeMillis() + unit.toMillis(timeout);
        return this;
    }

    public SamplerBuilder threadDumper(ThreadDumper threadDumper) {
        this.threadDumper = threadDumper;
        return this;
    }

    public SamplerBuilder threadGrouper(ThreadGrouper threadGrouper) {
        this.threadGrouper = threadGrouper;
        return this;
    }

    public Sampler start(Timer samplingThread) {
        Sampler sampler = new Sampler(this.samplingInterval, this.threadDumper, this.threadGrouper, this.timeout);
        sampler.start(samplingThread);
        return sampler;
    }

}
