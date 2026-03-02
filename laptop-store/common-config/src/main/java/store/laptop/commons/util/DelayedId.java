package store.laptop.commons.util;

import lombok.Getter;

import java.time.Duration;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Getter
public class DelayedId implements Delayed {
	private final Long id;
	private final long delayNanos;

	public DelayedId(Long id, Duration duration) {
		this.id = id;
		this.delayNanos = System.nanoTime() + duration.toNanos();
	}

	public long getDelay(TimeUnit unit) {
		long diff = delayNanos - System.nanoTime();
		return unit.convert(diff, TimeUnit.NANOSECONDS);
	}

	@Override public int compareTo(Delayed o) {
		return Long.compare(this.delayNanos, ((DelayedId) o).getDelayNanos());
	}
}
