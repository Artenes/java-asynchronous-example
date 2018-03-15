package main;

public class DB {
	
	private int mCurrentCount = 0;
	
	public void refresh() throws InterruptedException {
		System.out.println("Start image analysis");
		for (int count = 1; count <= 5; count++) {
			analyseImage(count);
		}
		cleanUpDB();
		wait();
	}
	
	private synchronized void analyseImage(int count) throws InterruptedException {
		mCurrentCount = count;
		Thread thread = new Thread(mRunnable);
		thread.start();
		wait();
	}
	
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			synchronized (DB.this) {
				try {
					System.out.println("Image analyzed #" + mCurrentCount);
					Thread.sleep(1000);
					DB.this.notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	private void cleanUpDB() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (DB.this) {
					try {
						Thread.sleep(2000);
						System.out.println("DB cleaned");
						DB.this.notify();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
}
