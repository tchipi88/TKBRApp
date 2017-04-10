(function() {
    'use strict';

    angular
        .module('app')
        .factory('BonReceptionLigneSearch', BonReceptionLigneSearch);

    BonReceptionLigneSearch.$inject = ['$resource'];

    function BonReceptionLigneSearch($resource) {
        var resourceUrl =  'api/_search/bon-reception-lignes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
