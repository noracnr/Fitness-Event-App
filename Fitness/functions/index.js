const functions = require('firebase-functions');

const algoliasearch = require('algoliasearch');
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


// [START init_algolia]
// Initialize Algolia, requires installing Algolia dependencies:
// https://www.algolia.com/doc/api-client/javascript/getting-started/#install
//
// App ID and API Key are stored in functions config variables
const ALGOLIA_ID = functions.config().algolia.appid;
const ALGOLIA_ADMIN_KEY = functions.config().algolia.apikey;
const ALGOLIA_SEARCH_KEY = functions.config().algolia.searchkey;

const ALGOLIA_INDEX_NAME = 'FitnessEvents';

const client = algoliasearch(ALGOLIA_ID, ALGOLIA_ADMIN_KEY);
const index = client.initIndex(ALGOLIA_INDEX_NAME);
/*
// [START update_index_function]
// Update the search index every time a blog post is written.
exports.onNoteCreated = functions.firestore.document('newevents/{id}').onCreate((snap, context) => {
  // Get the note document
  const note = snap.data();

  // Add an 'objectID' field which Algolia requires
  note.objectID = context.params.noteId;

  // Write to the algolia index
  const index = client.initIndex(ALGOLIA_INDEX_NAME);
  return index.saveObject(note);
});
// [END update_index_function]
*/
exports.addFirestoreDataToAlgolia = functions.firestore
	.document('events/{id}')
	.onCreate((snap, context) => {
		const data = snap.data();
		const objectID = snap.id;

		return index.addObject(data);
});

