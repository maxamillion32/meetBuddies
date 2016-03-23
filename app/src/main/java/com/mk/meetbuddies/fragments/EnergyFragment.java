package com.mk.meetbuddies.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mk.meetbuddies.R;
import com.mk.utils.EnergyConsumptionUtils;


/**
 * Created by Salim on 23/03/16.
 */
public class EnergyFragment extends Fragment {

    public EnergyConsumptionUtils energyUtils;
    private TextView cpu, memory, battery;
    Context context;
     public EnergyFragment(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_energy, container, false);

        energyUtils = new EnergyConsumptionUtils(getContext());
        cpu = (TextView) view.findViewById(R.id.cpu);
        memory = (TextView) view.findViewById(R.id.memory);
      //  battery = (TextView) view.findViewById(R.id.battery);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String cpuUse = Float.toString(energyUtils.readUsage());
                                String memUsage = Long.toString(energyUtils.getMemoryUsage());
                                String memSize = Long.toString(energyUtils.getUsedMemorySize());
                                String memUse = memSize + "/" + memUsage;
                                //   String batLevel = Float.toHexString(energyUtils.getBatteryLevel(getContext()));
                                cpu.setText(cpuUse + " %");
                                memory.setText(memUse);
                                //  battery.setText(batLevel);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        return view;
    }
public void setContext(Context c)
    {
        this.context=c;

    }
}
