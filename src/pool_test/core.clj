(ns pool-test.core
  (:gen-class)
  (:require [taoensso.carmine :as car]
            [taoensso.carmine.connections :as car-conn]))

(def redis-pool
  {:max-total-per-key 8
   :min-idle-per-key 2
   :max-wait-ms 2000})

(def redis-spec
  {:host "127.0.0.1"
   :port 6379})

(defn ping []
  (car/wcar {:spec redis-spec :pool redis-pool}
            (car/ping)))

(defn lazy-ping [ms]
  (Thread/sleep ms)
  (ping))

(defn ping-redis! []
  (try
    (println (ping))
    (catch Exception ex
      (println "Ping redis failed: " (.getMessage (.getCause ex))))))

(defn start [f interval num-threads]
  (while true
    (Thread/sleep interval)
    (dotimes [i num-threads]
      (future (f)))))

(defn -main [interval-ms num-threads & args]
  (if (or (nil? interval-ms) (nil? num-threads))
    (println "Usage: lein run interval-ms num-threads")
    (start ping-redis! (Long/parseLong interval-ms) (Long/parseLong num-threads))))

