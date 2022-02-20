package org.alkemy.challenge.java.configurations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper ModelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get(){
                return LocalDate.now();
            }
        };

        Converter<String, LocalDate> StringToDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
                LocalDate localDate = LocalDate.parse(source, formatter);
                return localDate;
            }
        };

        Converter<LocalDate, String> DateToString = new AbstractConverter<LocalDate, String>() {
            @Override
            protected String convert(LocalDate source) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
                String string = source.format(formatter);
                return string;
            }
        };

        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(StringToDate);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        modelMapper.createTypeMap(LocalDate.class, String.class);
        modelMapper.addConverter(DateToString);

        return modelMapper;
    }
    
}
