(function() {
    'use strict';

    angular
        .module('app')
        .controller('AccessDeleteController',AccessDeleteController);

    AccessDeleteController.$inject = ['$uibModalInstance', 'entity', 'Access'];

    function AccessDeleteController($uibModalInstance, entity, Access) {
        var vm = this;

        vm.access = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Access.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
