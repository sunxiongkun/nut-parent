package com.sxk.thread;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockSample {


  public static void main(String[] args) {
    LockSample lockSample = new LockSample();

  }

  private final Map<String, Data> m = new TreeMap<>();
  private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  private final Lock r = rwl.readLock();
  private final Lock w = rwl.writeLock();

  public Data get(String key) {
    r.lock();
    try {
      return m.get(key);
    } finally {
      r.unlock();
    }
  }

  public String[] allKeys() {
    r.lock();
    try {
      return m.keySet().toArray(new String[]{});
    } finally {
      r.unlock();
    }
  }

  public Data put(String key, Data value) {
    w.lock();
    try {
      return m.put(key, value);
    } finally {
      w.unlock();
    }
  }

  public void clear() {
    w.lock();
    try {
      m.clear();
    } finally {
      w.unlock();
    }
  }

}

@lombok.Data
class Data {

}
