(function() {
    'use strict';
    angular
        .module('app')
        .factory('Paie', Paie);

    Paie.$inject = ['$resource','DateUtils'];

    function Paie ($resource,DateUtils) {
        var resourceUrl =  'api/paies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateVersement =DateUtils.convertLocalDateFromServer(data.dateVersement);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateVersement =DateUtils.convertLocalDateToServer(copy.dateVersement);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateVersement =DateUtils.convertLocalDateToServer(copy.dateVersement);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
