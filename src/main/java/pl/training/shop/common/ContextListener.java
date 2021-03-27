package pl.training.shop.common;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.training.shop.common.profiler.Profiler;

@Component
@Log
public class ContextListener {

    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent refreshedEvent){
        log.info("Spring context refreshed");
    }

}
