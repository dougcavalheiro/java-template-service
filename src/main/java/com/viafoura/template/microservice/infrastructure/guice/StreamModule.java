package com.viafoura.template.microservice.infrastructure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.viafoura.common.vertx.kafka.KafkaStreamMonitorMetrics;
import com.viafoura.common.vertx.kafka.KafkaStreamSource;
import com.viafoura.common.vertx.kafka.KafkaStreamsSource;
import com.viafoura.common.vertx.kafka.TopologySupplier;
import com.viafoura.template.microservice.adapter.input.stream.EventsConsumerAdapter;
import com.viafoura.template.microservice.adapter.output.metrics.ServiceMetricsAdapter;
import com.viafoura.template.microservice.infrastructure.config.StreamConfig;
import com.viafoura.template.microservice.infrastructure.stream.EventConsumer;
import com.viafoura.template.microservice.infrastructure.stream.EventTopologySupplier;
import java.util.Properties;
import javax.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

public class StreamModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        bind(KafkaStreamSource.class).to(KafkaStreamsSource.class);
        bind(TopologySupplier.class).to(EventTopologySupplier.class);
        bind(EventConsumer.class).to(EventsConsumerAdapter.class);
        bind(KafkaStreamMonitorMetrics.class).to(ServiceMetricsAdapter.class);
    }

    @Provides
    @Singleton
    Producer<String, String> provideKafkaProducer(StreamConfig config) {
        Properties properties = getKafkaProducerConfig(config);
        return new KafkaProducer<>(properties);
    }

    Properties getKafkaProducerConfig(StreamConfig config) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, config.getApplicationId());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServer());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        return properties;
    }
}
