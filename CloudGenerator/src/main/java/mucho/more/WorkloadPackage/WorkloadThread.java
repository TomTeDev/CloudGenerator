package mucho.more.WorkloadPackage;

import com.google.common.collect.Queues;

import java.util.ArrayDeque;

public class WorkloadThread implements Runnable{
    private static  final  int MAX_MS_PER_TICKS = 1;
    public WorkloadThread(){
        workloadDeque = Queues.newArrayDeque();
    }
    private final ArrayDeque<Workload> workloadDeque;

    public void addLoad(Workload workload){
        workloadDeque.add(workload);
    }
    @Override
    public void run(){
        long stopTime = System.currentTimeMillis()+MAX_MS_PER_TICKS;
        while (!workloadDeque.isEmpty()&& System.currentTimeMillis()<=stopTime){
            workloadDeque.poll().compute();
        }
    }

}