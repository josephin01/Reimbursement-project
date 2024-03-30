import { initializeApp } from 'firebase/app'
import {getStorage} from 'firebase/storage'

// const firebaseConfig = {
//     apiKey: "AIzaSyAs5sbyrc9n14QufK0zhnUjB138XIycIF8",
//     authDomain: "reimbursement-73f31.firebaseapp.com",
//     projectId: "reimbursement-73f31",
//     storageBucket: "reimbursement-73f31.appspot.com",
//     messagingSenderId: "519174112830",
//     appId: "1:519174112830:web:b6311f8477c06385d5336f"
// };
const firebaseConfig = {
    apiKey: "AIzaSyBjUXFKi3yx9o3BbgALbkHNCgUUmPRSKbY",
    authDomain: "reimbursement-24d67.firebaseapp.com",
    projectId: "reimbursement-24d67",
    storageBucket: "reimbursement-24d67.appspot.com",
    messagingSenderId: "748636007486",
    appId: "1:748636007486:web:49250a551a30d1b6fab56f"
  };
const app=initializeApp(firebaseConfig)
export const storage=getStorage(app)