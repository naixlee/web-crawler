package Dispatcher;

import InputReader.Seed;


/**
 * The class Task is designed to wrap up a URL seed and the depth of the url from the starting url.
 *
 * Created by xli1 on 8/13/15.
 */
public class Task {
  private Seed _seed;
  private int _depth;
  public Task(Seed seed, int depth) {
    _seed = seed;
    _depth = depth;
  }
  public Seed getSeed() {
    return _seed;
  }

  public void setSeed(Seed seed) {
    _seed = seed;
  }

  public int getDepth() {
    return _depth;
  }

  public void setDepth(int depth) {
    _depth = depth;
  }
}

