(function() {
    'use strict';

    angular
        .module('app')
        .factory('CaisseMouvementSearch', CaisseMouvementSearch);

    CaisseMouvementSearch.$inject = ['$resource'];

    function CaisseMouvementSearch($resource) {
        var resourceUrl =  'api/_search/caisse-mouvements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
