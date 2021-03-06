(ns verbal-expressions.core)

(def start-of-line
  (fn [] "^"))

(defn then [value]
  (fn [] value))

(defn maybe [value]
  (fn [] (str "(" value ")?")))

(defn anything-but [value]
  (fn [] (str "[^" value "]+")))

(def end-of-line
  (fn [] "$"))

; TODO

; modifiers
;   anything()
;   find(value)

; special characters and groups
;   any(value)
;   anyOf(value)
;   br()
;   lineBreak()
;   range(from, to)
;   tab()
;   word()

; modifiers
;   withAnyCase()
;   stopAtFirst()
;   searchOneLine()

; functions
;   replace(source, value)

; other
;   add( expression )
;   multiple( value )
;   or()

(defn- translate-to-regex [expr]
  (loop [result ""
         list expr]
    (if (empty? list)
        result
        (recur (str result ((first list)))
               (rest list)))))

(defn verbal-expression [& expr]
  (let [expression (translate-to-regex expr)
        regex      (re-pattern expression)]
    (fn [x] (not (empty? (re-seq regex x))))))