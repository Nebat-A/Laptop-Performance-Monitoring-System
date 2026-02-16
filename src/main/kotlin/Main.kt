import io.prometheus.client.Gauge
import io.prometheus.client.exporter.HTTPServer
import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.GlobalMemory

fun main() {

    // Prometheus metrics
    val cpuUsage = Gauge.build()
        .name("laptop_cpu_usage_percent")
        .help("Current CPU usage percentage")
        .register()

    val memoryUsed = Gauge.build()
        .name("laptop_memory_used_bytes")
        .help("Memory used in bytes")
        .register()

    val threadCount = Gauge.build()
        .name("jvm_thread_count")
        .help("Current JVM thread count")
        .register()

    // Start HTTP server for Prometheus to scrape
    HTTPServer(9091)

    val systemInfo = SystemInfo()
    val processor: CentralProcessor = systemInfo.hardware.processor
    val memory: GlobalMemory = systemInfo.hardware.memory

    println("Metrics server started at http://localhost:9091/metrics")

    var previousTicks = processor.systemCpuLoadTicks

    while (true) {

        val cpu = processor.getSystemCpuLoadBetweenTicks(previousTicks) * 100
        previousTicks = processor.systemCpuLoadTicks

        val usedMem = memory.total - memory.available
        val threads = Thread.activeCount()

        cpuUsage.set(cpu)
        memoryUsed.set(usedMem.toDouble())
        threadCount.set(threads.toDouble())

        Thread.sleep(5000)
    }
}