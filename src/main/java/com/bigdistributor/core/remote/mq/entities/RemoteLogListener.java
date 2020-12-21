package com.bigdistributor.core.remote.generic;

import com.bigdistributor.core.remote.mq.entities.KafkaMessage;


/**
 * Observer mode event listener
 * used for log event
 * Can be used by GUI, Add to DB ...
 *
 * @author Marwan Zouinkhi
 * @see com.bigdistributor.core.remote.mq.kafka.KafkaLogDispatchManager
 */
public interface RemoteLogListener {
    void onLogAdded(KafkaMessage message);
}
