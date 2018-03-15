package main;

public class Service {

	private DB mDb = new DB();
	
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				synchronized (mDb) {
					mDb.refresh();
					schedule();
					finishJob();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
	public boolean startJob() {
		Thread thread = new Thread(mRunnable);
		thread.start();
		return true;
	}
	
	public void finishJob() {
		System.out.println("Finished");
	}
	
	public void schedule() {
		System.out.println("Scheduled");
	}
	
}
