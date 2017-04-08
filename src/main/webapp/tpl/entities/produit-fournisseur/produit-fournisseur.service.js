(function() {
    'use strict';
    angular
        .module('tkbrApp')
        .factory('ProduitFournisseur', ProduitFournisseur);

    ProduitFournisseur.$inject = ['$resource'];

    function ProduitFournisseur ($resource) {
        var resourceUrl =  'api/produit-fournisseurs/:id';

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
