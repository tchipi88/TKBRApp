(function() {
    'use strict';
    angular
        .module('tkbrApp')
        .factory('ProduitCategorie', ProduitCategorie);

    ProduitCategorie.$inject = ['$resource'];

    function ProduitCategorie ($resource) {
        var resourceUrl =  'api/produit-categories/:id';

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
