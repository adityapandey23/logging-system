package tech.thedumbdev.data;

import tech.thedumbdev.pojo.Log;

import java.util.concurrent.TimeoutException;

public class Filestore implements Datastore{
    @Override
    public void addLog(Log log) {}

    @Override
    public void appendLog() throws TimeoutException {}
}
