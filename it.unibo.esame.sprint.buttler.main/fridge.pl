%====================================================================================
% fridge description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxbutler, "localhost",  "MQTT", "0" ).
 qactor( butler, ctxbutler, "it.unibo.butler.Butler").
