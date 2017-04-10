(function() {
    'use strict';

    angular
        .module('app')
        .factory('DecaissementSearch', DecaissementSearch);

    DecaissementSearch.$inject = ['$resource'];

    function DecaissementSearch($resource) {
        var resourceUrl =  'api/_search/decaissements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
