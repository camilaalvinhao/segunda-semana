(ns segunda-semana.core
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [segunda-semana.db :as db]
            [segunda-semana.model :as model]))

(def conn (db/abre-conexao))
(db/cria-schema conn)

(def cliente (model/novo-cliente "Jose" "Silva" "1010101010" "jose@jose.com"))
(def cliente1 (model/novo-cliente "Orlando" "Oliveira" "1212121212" "orlando@orlando.com"))
(d/transact conn [cliente, cliente1])

(def cartao (model/novo-cartao 5336742783042220 430 #inst "2022-09-16T00:00" 8000M [:cliente/id (:cliente/id cliente)]))
(d/transact conn [cartao])

(def categoria (model/nova-categoria "Supermercado" "/supermercado"))
(def categoria1 (model/nova-categoria "Farmacia" "/farmacia"))
(def categoria2 (model/nova-categoria "Padaria" "/padaria"))
(d/transact conn [categoria, categoria1, categoria2])

(def compra (model/nova-compra #inst "2020-09-16T07:01",
                               10.8M,
                               "Supermercado BH",
                               [:categoria/id (:categoria/id categoria1)],
                               [:cartao/id (:cartao/id cartao)]))

(d/transact conn [compra])

(pprint (db/todas-as-compras (d/db conn)))
(pprint (db/todos-os-clientes (d/db conn)))
(pprint (db/todos-os-cartoes (d/db conn)))
(pprint (db/todos-as-categorias (d/db conn)))

(db/apaga-banco)
