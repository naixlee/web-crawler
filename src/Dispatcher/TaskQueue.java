package Dispatcher;

import java.util.LinkedList;


/**
 * Created by xli1 on 8/13/15.
 */

public class TaskQueue {
  /**
   * The maximum level that the crawler will explore.
   */
  private int MAX_LEVEL;
  private LinkedList<Task> _taskList;
  public TaskQueue() {
    MAX_LEVEL = 1;
    _taskList = new LinkedList<Task>();
  }
  public TaskQueue(int maxLevel) {
    this();
    MAX_LEVEL = maxLevel;
  }

  public LinkedList<Task> getTaskList() {
    return _taskList;
  }

  public void setTaskList(LinkedList<Task> taskList) {
    _taskList = taskList;
  }

  public synchronized void push(Task task) {
    if (task.getDepth() > MAX_LEVEL) return;
    _taskList.addLast(task);
  }

  public synchronized Task pop() {
    if (isEmpty()) return null;
    return _taskList.pop();
  }

  public boolean isEmpty() {
    return _taskList.isEmpty();
  }
}
