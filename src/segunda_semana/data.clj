(ns segunda-semana.data)

(def cliente (model/novo-cliente "Jose" "Silva" "1010101010" "jose@jose.com"))
(def cliente1 (model/novo-cliente "Orlando" "Oliveira" "1212121212" "orlando@orlando.com"))

(def cartao (model/novo-cartao 5336742783042220 430 #inst "2022-09-16T00:00" 8000M [:cliente/id (:cliente/id cliente)]))
(def cartao1 (model/novo-cartao 5336742783042312 942 #inst "2027-09-16T00:00" 5000M [:cliente/id (:cliente/id cliente2)]))

(def categoria (model/nova-categoria "Supermercado" "/supermercado"))
(def categoria1 (model/nova-categoria "Farmacia" "/farmacia"))
(def categoria2 (model/nova-categoria "Padaria" "/padaria"))

(def compra (model/nova-compra #inst "2020-09-16T07:01",
                               10.8M,
                               "Supermercado BH",
                               [:categoria/id (:categoria/id categoria)],
                               [:cartao/id (:cartao/id cartao)]))

(def compra1 (model/nova-compra #inst "2021-04-17T22:31",
                                15.2M,
                                "Pao Fofo",
                                [:categoria/id (:categoria/id categoria2)],
                                [:cartao/id (:cartao/id cartao)]))

(def compra2 (model/nova-compra #inst "2020-12-02T20:31",
                                102.9M,
                                "Carrefour",
                                [:categoria/id (:categoria/id categoria)],
                                [:cartao/id (:cartao/id cartao)]))

(defn insere-dados! [conn]
  (d/transact conn [cliente, cliente1])
  (d/transact conn [cartao, cartao1])
  (d/transact conn [categoria, categoria1, categoria2])
  (d/transact conn [compra compra1 compra2])
  )