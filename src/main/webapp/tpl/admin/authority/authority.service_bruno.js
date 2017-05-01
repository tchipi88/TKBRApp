(function() {
    'use strict';
    angular
        .module('app')
        .factory('Authority2', Authority2);

    Authority2.$inject = ['$resource'];

    function Authority2 ($resource) {
        var resourceUrl =  'api/authoritys/string/:id';

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
