(function() {
    'use strict';
    angular
        .module('app')
        .factory('BonLivraisonLigne', BonLivraisonLigne);

    BonLivraisonLigne.$inject = ['$resource'];

    function BonLivraisonLigne ($resource) {
        var resourceUrl =  'api/bon-livraison-lignes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
