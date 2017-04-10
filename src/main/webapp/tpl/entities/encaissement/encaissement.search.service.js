(function() {
    'use strict';

    angular
        .module('app')
        .factory('EncaissementSearch', EncaissementSearch);

    EncaissementSearch.$inject = ['$resource'];

    function EncaissementSearch($resource) {
        var resourceUrl =  'api/_search/encaissements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
