/*
MIT License

Copyright (c) 2016 Victor J Grazi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.vgrazi.play;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Victor J Grazi
 */
//source: https://github.com/vgrazi/rxjava-snippets/
public class SomeFeed<T> {
	private final boolean barriered;
	private AtomicInteger threadcounter = new AtomicInteger(1);

	private ExecutorService service = Executors.newCachedThreadPool(r -> {
		Thread thread = new Thread(r);
		thread.setName("Thread " + threadcounter.getAndIncrement());
		return thread;
	});
	private transient boolean running = true;

	private List<SomeListener> listeners = new LinkedList<>();
	private int threadCount;
	private CyclicBarrier barrier;

	private final Random RANDOM = new Random(0);
	private static final Random RANDOM_PRICE = new Random(0);

	private static final String[] instruments = {"IBM", "NMR", "BAC", "AAPL", "MSFT"};

	public SomeFeed() {
		this(instruments.length);
	}

	public SomeFeed(int threadCount) {
		this(threadCount, false);
	}

	public SomeFeed(int threadCount, boolean barriered) {
		this.threadCount = threadCount;
		this.barriered = barriered;
		if (barriered) {
			barrier = new CyclicBarrier(threadCount, System.out::println);
		}
		launchPublishers();
	}


	AtomicInteger sequence = new AtomicInteger(1);
	private void launchEventThread(String instrument, double startingPrice) {
		service.execute(() ->
		{
			final Object MUTEX = new Object();
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
			double price = startingPrice;
			while (running) {
				try {
					if (barriered) {
						barrier.await();
					}
					price +=  RANDOM_PRICE.nextGaussian();

					double finalPrice = price;
					listeners.forEach(subscriber -> {
						PriceTick tick = new PriceTick(sequence.getAndIncrement(), new Date(), instrument, finalPrice);
						String message = String.format("%s %s %s", format.format(new Date()), instrument, finalPrice);
//            Logger.print("Notifying " + message);
						subscriber.priceTick(tick);
					});
					synchronized (MUTEX) {
						MUTEX.wait(RANDOM.nextInt(200) + 800);
					}
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		});
	}

	double[] prices = {160, 5, 15,  108, 57};
	void launchPublishers() {
		System.out.println("Launching publishers");
		for (int i = 0; i < threadCount; i++) {
			launchEventThread(instruments[i%instruments.length], prices[i%prices.length]);
		}
	}

	public void register(SomeListener listener) {
		System.out.println("Registering subscriber " + listener);
		listeners.add(listener);
	}

	public void terminate() {
		running = false;
	}

}