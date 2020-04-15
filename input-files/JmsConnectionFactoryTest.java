private void putTopic(List<String> events) throws Exception {
  ConnectionFactory factory = new ActiveMQConnectionFactory(USERNAME,
      PASSWORD, BROKER_BIND_URL);
  Connection connection = factory.createConnection();
  connection.start();

  Session session = connection.createSession(true,
      Session.AUTO_ACKNOWLEDGE);
  Destination destination = session.createTopic(DESTINATION_NAME);
  MessageProducer producer = session.createProducer(destination);

  for (String event : events) {
    TextMessage message = session.createTextMessage();
    message.setText(event);
    producer.send(message);
  }
  session.commit();
  session.close();
  connection.close();
}

@ReactMethod
public void close(String id, Promise promise) {
    try {
        Graph graph = graphs.get(id);
        graph.close();
        promise.resolve(true);
    } catch (Exception e) {
        promise.reject(e);
    }
}