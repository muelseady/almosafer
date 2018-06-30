const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.PushNotification = functions.database.ref('/unAnsweredOffers/{pushId}')
    .onCreate((snapshot, context) => {

      // Grab the current value of what was written to the Realtime Database.
      const original = snapshot.val();
      console.log(context.params.pushId, original);

      const payload = {
              notification: {
                  title:'newOffer',
                  body: original.userName ,
                  sound: "default"
      }
    };

    const empls = []

    const ref =  admin.database().ref(`/employeeNotification/{employeeId}`)
         .once('value' , function(snapshot) {
              snapshot.forEach(function(child) {

    console.log(child);
             });

             return admin.messaging().sendToDevice(child, payload);


         });

      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to the Firebase Realtime Database.
      // return admin.messaging().sendToDevice(token, payload);
    });

//Export notification upon any new messages from employee or user
exports.PushMessageNotification = functions.database.ref('/users/{userId}/goingoffers/{offerId}/messages/{messageId}')
        .onCreate((snapshot, context) => {

        const userId = context.params.userId;
        const offerId = context.params.offerId;
        const senderCat = snapshot.val().senderCategory;

        console.log(senderCat);

        const payload = {
                notification: {
                      title:'رساله جديده',
                      body: '  من  ' + snapshot.val().senderName  ,
                      sound: "default"
                    }
                  };


        const employee = "Employee";

        const ref =  admin.database()
          .ref(`/users/${userId}/goingoffers/${offerId}`)
          .orderByChild('adults')
          .once('value').then(snapshot => {
                 // there, I queried!

               const employeeToken = snapshot.val().Employee;
               const userToken = snapshot.val().userIdToken;

               console.log(employeeToken);
               console.log(userToken);

              if(!senderCat.localeCompare(employee)) {
                return admin.messaging().sendToDevice(userToken, payload);
              } else {
                return admin.messaging().sendToDevice(employeeToken, payload);
              }
               });



          // You must return a Promise when performing asynchronous tasks inside a Functions such as
          // writing to the Firebase Realtime Database.
        });
