package com.example.app.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para debugging y demostración de resultados.
 *
 * <p>Este controlador permite mostrar en consola el flujo de ejecución y los resultados de las
 * operaciones para fines educativos.
 */
@RestController
@RequestMapping("/debug")
public class DebugController {

  private static final Logger log = LoggerFactory.getLogger(DebugController.class);

  /** Endpoint para mostrar información de debugging en consola. */
  @GetMapping("/info")
  public String debugInfo() {
    log.info("=== DEBUG INFO ===");
    log.info("Application is running in DEBUG mode");
    log.info("Breakpoints can be set in any method");
    log.info("Console will show detailed execution flow");
    log.info("==================");

    return "Debug mode active. Check console for detailed logs.";
  }

  /** Endpoint para simular operaciones y mostrar resultados. */
  @PostMapping("/simulate")
  public String simulateOperation(@RequestBody String operation) {
    log.info("=== SIMULATING OPERATION ===");
    log.info("Operation: {}", operation);
    log.info("This is where you can set breakpoints");
    log.info("Step through the code line by line");
    log.info("Watch variables change in real-time");
    log.info("=============================");

    return "Operation simulated. Check console for step-by-step execution.";
  }

  /** Endpoint para mostrar métricas de performance. */
  @GetMapping("/metrics")
  public String showMetrics() {
    log.info("=== PERFORMANCE METRICS ===");
    log.info("Memory usage: {} MB", Runtime.getRuntime().totalMemory() / 1024 / 1024);
    log.info("Free memory: {} MB", Runtime.getRuntime().freeMemory() / 1024 / 1024);
    log.info("Active threads: {}", Thread.activeCount());
    log.info("===========================");

    return "Metrics logged to console.";
  }
}
