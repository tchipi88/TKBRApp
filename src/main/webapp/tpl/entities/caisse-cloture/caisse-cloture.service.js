(function() {
    'use strict';
    angular
        .module('app')
        .factory('CaisseCloture', CaisseCloture);

    CaisseCloture.$inject = ['$resource'];

    function CaisseCloture ($resource) {
        var resourceUrl =  'api/caisse-clotures/:id';

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
