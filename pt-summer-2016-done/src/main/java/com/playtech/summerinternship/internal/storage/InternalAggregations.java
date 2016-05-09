package com.playtech.summerinternship.internal.storage;

import com.playtech.summerinternship.domain.AggregationKey;
import com.playtech.summerinternship.domain.AvgMax;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InternalAggregations implements Serializable {

    private Map<AggregationKey, AvgMax> aggregations = new HashMap<>();

    public int size() {
        return aggregations.size();
    }

    public Set<Map.Entry<AggregationKey, AvgMax>> entrySet() {
        return aggregations.entrySet();
    }

    public boolean containsKey(AggregationKey key) {
        return aggregations.containsKey(key);
    }

    public AvgMax get(AggregationKey key) {
        return aggregations.get(key);
    }

    public AvgMax put(AggregationKey key, AvgMax avgMax) {
        return aggregations.put(key, avgMax);
    }

    public InternalAggregations(File file) {
        InternalAggregations  aggregations = null;
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fin);
            aggregations = (InternalAggregations) ois.readObject();
            fin.close(); ois.close();
        } catch (IOException e) {
            this.aggregations = new HashMap<>();
            return;
        } catch (ClassNotFoundException e) {
            this.aggregations = new HashMap<>();
            return;
        }

        if(aggregations.aggregations != null) {
            this.aggregations.putAll(aggregations.aggregations);
        } else {
            this.aggregations = new HashMap<>();
        }
    }

    public void save(File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(os);
            output.writeObject(this);
            os.close(); output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
