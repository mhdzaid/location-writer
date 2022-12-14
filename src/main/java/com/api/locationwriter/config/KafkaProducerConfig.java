package com.api.locationwriter.config;


import com.api.locationwriter.model.Location;
import com.api.locationwriter.util.LocationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig
{
    private final LocationProperties locationProperties;
    private final ObjectMapper objectMapper;

    /**
     * Producer factory for Serialization of Location
     * @return
     */
    @Bean
    public ProducerFactory<String, Location> producerFactory()
    {
        return new DefaultKafkaProducerFactory<>(producerConfigs(),
                stringKeySerializer(),
                kafkaMessageSerializer());
    }

    /**
     * Configuring kafka bootstrap server for producer
     * @return
     */
    @Bean
    public Map<String, Object> producerConfigs()
    {
        String bootstrapServer = locationProperties.getKafka().getBootstrapServer();
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return props;
    }


    /**
     * Config for kafkaTemplate handling location
     * @return
     */
    @Bean
    public KafkaTemplate<String, Location> locationKafkaTemplate()
    {
        KafkaTemplate<String, Location> kafkaTemplate =  new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }

    /**
     * Registering String Serializer for kafka producer
     * @return
     */
    @Bean
    public Serializer stringKeySerializer()
    {
        return new StringSerializer();
    }

    /**
     * Registering JsonSerializer for kafka producer
     * @return
     */
    @Bean
    public Serializer kafkaMessageSerializer()
    {
        return new JsonSerializer(objectMapper);
    }
}
