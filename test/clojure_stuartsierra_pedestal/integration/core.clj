(ns clojure-stuartsierra-pedestal.integration.core
  (:require [clojure-stuartsierra-pedestal.infra.configuration.component :as component]
            [clojure.test :refer :all]
            [io.pedestal.test :refer [response-for]]))

(def system (atom nil))

(use-fixtures :once
              (fn [tests]
                (reset! system (component/start component/system-component-dev))
                (try
                  (tests)
                  (finally
                    (component/stop @system)))))

(deftest test-routes
  (testing "Mock route responds correctly"
    (let [service (-> @system :pedestal :service :io.pedestal.http/service-fn)
          response (response-for service :get "/url-page?page=1&size=1")]
      (is (= 200 (:status response))))))