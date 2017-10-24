(ns paratydigital.db)

(def initial-route {:panel :home-panel})

(def default-db
  {:title "Paraty Digital"
   :back-button false
   :active-route initial-route
   :history [initial-route]
   :args nil
   :user {:_id 22222
          :name "Alan Leal"
          :avatar "https://facebook.github.io/react/img/logo_og.png"}
   :home-highlights {0 {:name "Destaque"}
                     1 {:name "Bombando Agora"}
                     2 {:name "Novidade"}
                     3 {:name "Mais Vistos"}}
   :categories {0 {:name "Guia comercial"
                   :icon "restaurant"
                   :color :lightGreen700}
                1 {:name "Promoções"
                   :icon "hotel"
                   :color :lightGreen700}
                2 {:name "Passeios"
                   :icon "beach-access"
                   :color :red400}
                3 {:name "Eventos"
                   :icon "local-bar"
                   :color :orange800}
                4 {:name "Notícias"
                   :icon "terrain"
                   :color :blue600}
                5 {:name "Classificados"
                   :icon "directions-car"
                   :color :blue500}}

   :ads {0 {:name "Lojas de Paraty"
            :headline "Anunciam aqui"
            :category 0}
         1 {:name "Che Lagarto"
            :headline "O melhor hostel"
            :category 1}
         2 {:name "Antiquaria Restaurante"
            :headline "paraty do passado"
            :category 1}
         3 {:name "Cafe no bule"
            :headline "melhor que o do ratinho"
            :category 2}
         4 {:name "Chapelaria chapeleu"
            :headline "Melhor que o zebeleu"
            :category 1}
         5 {:name "Hortifruti Paraty"
            :headline "e Para todo mundo"
            :category 2}}

   :threads {0
             {:name "Guia de Paraty"
              :ad-key 0
              :messages [{:_id 1
                          :text "Bom dia!"
                          :createdAt (js/Date.)}
                         {:_id 2
                          :text "Seja bem-vindo(a) ao Paraty Digital! Eu serei o seu guia por aqui :)"
                          :createdAt (js/Date.)}]}}})
