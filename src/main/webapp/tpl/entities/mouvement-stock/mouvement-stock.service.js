(function() {
    'use strict';
    angular
        .module('app')
        .factory('MouvementStock', MouvementStock);

    MouvementStock.$inject = ['$resource'];

    function MouvementStock ($resource) {
        var resourceUrl =  'api/mouvement-stocks/:id';

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
