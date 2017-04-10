(function() {
    'use strict';

    angular
        .module('app')
        .factory('MouvementStockSearch', MouvementStockSearch);

    MouvementStockSearch.$inject = ['$resource'];

    function MouvementStockSearch($resource) {
        var resourceUrl =  'api/_search/mouvement-stocks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
