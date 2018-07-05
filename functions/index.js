const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.PushNotification = functions.database.ref('/unAnsweredOffers/{pushId}')
    .onCreate((snapshot, context) => {

      // Grab the current value of what was written to the Realtime Database.
      const original = snapshot.val();

      let key = "not specified";

      if (original.employeeKey !== null) {
        key = original.employeeKey
      }

      const payload = {
              data: {
                  title: ' طلب جديد ' + original.offerName,
                  body: '  من  ' + original.userName ,
                  categ: 'newOffer',
                  employeeKey: key
      }
    };

    var empls = [];

    const ref =  admin.database().ref('/employeeNotification/')
    .once('value').then(snapshot => {
      const value = snapshot.val();
            snapshot.forEach(function(child) {
              empls.push(child.val());
            });
      return admin.messaging().sendToDevice(empls, payload);

      // Update the parent. This effectively removes the extra children.
    })
    .catch(function(e) {
      console.log(e);
    });


      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to the Firebase Realtime Database.
      return 0;
    });

//Export notification upon any new messages from employee or user
exports.PushMessageNotification = functions.database.ref('/users/{userId}/goingoffers/{offerId}/messages/{messageId}')
        .onCreate((snapshot, context) => {

        const userId = context.params.userId;
        const offerId = context.params.offerId;
        const senderCat = snapshot.val().senderCategory;

        console.log(senderCat);

        //Notification data to be send to the targeted device
        const payload = {
                data: {
                    "userId": userId,
                    "offerId": offerId,
                    "senderName" : snapshot.val().senderName
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
          return null;
        });
