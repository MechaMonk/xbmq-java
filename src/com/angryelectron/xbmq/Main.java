/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angryelectron.xbmq;

import com.angryelectron.xbmq.listener.XbmqSampleReceiveListener;
import com.angryelectron.xbmq.listener.XbmqDataReceiveListener;
import com.angryelectron.xbmq.listener.XbmqMqttCallback;
import com.angryelectron.xmbq.message.MqttAtMessage;
import com.angryelectron.xmbq.message.MqttDataMessage;
import com.angryelectron.xmbq.message.MqttDiscoveryMessage;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author abythell
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws com.digi.xbee.api.exceptions.XBeeException
     * @throws org.eclipse.paho.client.mqttv3.MqttException
     */
    public static void main(String[] args) throws XBeeException, MqttException {

        Xbmq xbmq = Xbmq.getInstance();
        xbmq.connect();

        /**
         * Setup listeners for unsolicited packets from the XBee network.
         */
        XBeeDevice xbee = xbmq.getXBee();
        xbee.addDataListener(new XbmqDataReceiveListener());
        xbee.addIOSampleListener(new XbmqSampleReceiveListener());
        
        /**
         * Subscribe to topics.
         */
        String[] topics = {
            MqttDataMessage.getSubscriptionTopic(),   
            MqttAtMessage.getSubscriptionTopic(),
            MqttDiscoveryMessage.getSubscriptionTopic()
        };
        int[] qos = {0, 0, 0};
        
        MqttAsyncClient mqtt = xbmq.getMqttClient();
        mqtt.setCallback(new XbmqMqttCallback());
        mqtt.subscribe(topics, qos);

    }

}