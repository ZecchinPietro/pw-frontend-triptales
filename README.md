# TripTales – Frontend Android (Jetpack Compose + ML Kit)

Questo repository contiene il **frontend Android** del progetto **TripTales – Diario di Gita con Intelligenza Artificiale**, realizzato con **Jetpack Compose**, **Kotlin** e **Firebase ML Kit**.  
L'app permette agli studenti di documentare, commentare e condividere momenti salienti di una gita scolastica in modo smart, collaborativo e geolocalizzato.

---

## 🎯 Funzionalità principali

- ✅ Registrazione e login con JWT
- ✅ Creazione e partecipazione a gruppi gita
- ✅ Diario condiviso (foto + testo)
- ✅ Commenti tra utenti del gruppo
- ✅ Geolocalizzazione delle foto su mappa
- ✅ Integrazione con **ML Kit**:
  - Riconoscimento testo (OCR)
  - Traduzione automatica
  - Riconoscimento oggetti
  - Riconoscimento volti + sticker
  - Didascalie automatiche (Smart Caption)
- ✅ Badge e classifica utenti attivi

---

## 🧰 Tecnologie utilizzate

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin](https://kotlinlang.org/)
- [Firebase ML Kit](https://firebase.google.com/products/ml)
- [Google Maps API](https://developers.google.com/maps)
- [Retrofit](https://square.github.io/retrofit/) – per comunicazione HTTP
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- JWT (JSON Web Token) – per l'autenticazione

---

## ⚙️ Setup del progetto

1. Clona il repository:
   ```bash
   git clone https://github.com/tuo-utente/pw-frontend-triptales.git

    Apri il progetto in Android Studio.

    Controlla che l'URL del backend sia corretto:
    In RetrofitInstance.kt o simile:

    const val BASE_URL = "http://10.0.2.2:8000/api/"

        10.0.2.2 è l'indirizzo per accedere a localhost dal simulatore Android.

    Assicurati di avere le chiavi API per:

        Firebase ML Kit (OCR, traduzioni, oggetti, volti)

        Google Maps API

🔐 Autenticazione JWT

Dopo aver effettuato il login, l'app riceve un Access Token JWT dal backend.
Questo token deve essere inviato in ogni chiamata API protetta nel formato:

Authorization: Bearer <access_token>

📂 Struttura del progetto

pw-frontend-triptales/
├── ui/                 # Schermate e componenti Compose
│   ├── screens/        # Schermate (Login, Home, Gruppi, Diario...)
│   ├── components/     # Componenti riutilizzabili (bottoni, card, ecc.)
├── data/               # Comunicazione API con Retrofit
│   ├── api/            # Interfacce e istanza Retrofit
├── model/              # Classi dati (DTO)
├── viewmodel/          # Gestione stato e logica business
├── MainActivity.kt     # Activity principale
└── ...

🧪 Debug e test

    Testa le API con Postman se necessario.

    Usa il Logcat per verificare i token JWT ricevuti.

    Prova l'app su un emulatore o un dispositivo reale.

📝 Note

    Backend Django disponibile nel repo pw-backend-triptales

    Database MySQL: pwtriptales_db (via XAMPP)

    L'app supporta l'invio e la ricezione dei metadati generati tramite ML Kit (es. OCR, tag oggetti, descrizioni automatiche).

Uso didattico – Istituto Tecnico Informatico ITIS ROSSI Quinta Superiore.

Crediti: Favero Mattia, Minante Davide, Zecchin Pietro.
