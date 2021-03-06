(ns segunda-semana.core
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [segunda-semana.db :as db]
            [segunda-semana.model :as model]
            [segunda-semana.data :as data]))

(def conn (db/abre-conexao!))
(db/cria-schema! conn)
(data/insere-dados! conn)


(pprint (db/todas-as-compras (d/db conn)))
(pprint (db/todos-os-clientes (d/db conn)))
(pprint (db/todos-os-cartoes (d/db conn)))
(pprint (db/todos-as-categorias (d/db conn)))

(db/apaga-banco)
