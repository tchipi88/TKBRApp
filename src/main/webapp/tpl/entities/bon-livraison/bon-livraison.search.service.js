(function() {
    'use strict';

    angular
        .module('app')
        .factory('BonLivraisonSearch', BonLivraisonSearch);

    BonLivraisonSearch.$inject = ['$resource'];

    function BonLivraisonSearch($resource) {
        var resourceUrl =  'api/_search/bon-livraisons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
