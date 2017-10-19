(ns paratydigital.db)

(def initial-route {:panel :home-panel})

(def default-db
  {:title "Paraty Digital"
   :back-button false
   :active-route initial-route
   :history [initial-route]
   :args nil
   :home-highlights [{:key 1
                      :name "Destaque"}
                     {:key 2
                      :name "Bombando Agora"}
                     {:key 3
                      :name "Novidade"}
                     {:key 4
                      :name "Mais Vistos"}]
   :categories [{:id 1
                 :key 1
                 :name "Gastronomia"
                 :icon "restaurant"
                 :color :lightGreen700}
                {:id 2
                 :key 2
                 :name "Hospedagem"
                 :icon "hotel"
                 :color :lightGreen700}
                {:id 3
                 :key 3
                 :name
                 "Passeios"
                 :icon "beach-access"
                 :color :red400}
                {:id 4
                 :key 4
                 :name "Eventos"
                 :icon "local-bar"
                 :color :orange800}
                {:id 5
                 :key 5
                 :name "Cidade"
                 :icon "terrain"
                 :color :blue600}
                {:id 6
                 :key 6
                 :name "Anuncios e Vagas"
                 :icon "directions-car"
                 :color :blue500}]

   :ads [{:id 1
          :key 1
          :name "Lojas de Paraty"
          :headline "Anunciam aqui"
          :category 1}
         {:id 2
          :key 2
          :name "Che Lagarto"
          :headline "O melhor hostel"
          :category 2}
         {:id 3
          :key 3
          :name "Antiquaria Restaurante"
          :headline "paraty do passado"
          :category 1}
         {:id 4
          :key 4
          :name "Cafe no bule"
          :headline "melhor que o do ratinho"
          :category 2}
         {:id 5
          :key 5
          :name "Chapelaria chapeleu"
          :headline "Melhor que o zebeleu"
          :category 1}
         {:id 6
          :key 6
          :name "Hortifruti Paraty"
          :headline "e Para todo mundo"
          :category 2}]

   :threads
   [
    {:id 1
     :key 1
     :name "Guia de Paraty"
     :user {:_id 1
            :name "Guia de Paraty"
            :avatar "https://facebook.github.io/react/img/logo_og.png"}
     :messages [{:_id 1
                 :text "Bom dia!"
                 :createdAt (js/Date.)}
                {:_id 2
                 :text "Seja bem-vindo(a) ao Paraty Digital! Eu serei o seu guia por aqui :)"
                 :createdAt (js/Date.)}]}]})
