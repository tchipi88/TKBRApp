(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('UniteDetailController', UniteDetailController);

    UniteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Unite'];

    function UniteDetailController($scope, $rootScope, $stateParams, previousState, entity, Unite) {
        var vm = this;

        vm.unite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:uniteUpdate', function(event, result) {
            vm.unite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
