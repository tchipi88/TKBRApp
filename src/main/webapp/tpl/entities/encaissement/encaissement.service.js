(function() {
    'use strict';
    angular
        .module('app')
        .factory('Encaissement', Encaissement);

    Encaissement.$inject = ['$resource','DateUtils'];

    function Encaissement ($resource,DateUtils) {
        var resourceUrl =  'api/encaissements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateMouvement =DateUtils.convertLocalDateFromServer(data.dateMouvement);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateMouvement =DateUtils.convertLocalDateToServer(copy.dateMouvement);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateMouvement =DateUtils.convertLocalDateToServer(copy.dateMouvement);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
