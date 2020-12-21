package com.bigdistributor.core.remote.mq.entities;

import com.bigdistributor.core.remote.mq.MQLogReceiveDispatchManager;


/**
 * Observer mode event listener
 * used for log event
 * Can be used by GUI, Add to DB ...
 *
 * @author Marwan Zouinkhi
 * @see MQLogReceiveDispatchManager
 */
public interface RemoteLogListener {
    void onLogAdded(MQMessage message);
}
