(function() {
    'use strict';
    angular
        .module('app')
        .factory('BonLivraison', BonLivraison);

    BonLivraison.$inject = ['$resource'];

    function BonLivraison ($resource) {
        var resourceUrl =  'api/bon-livraisons/:id';

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
